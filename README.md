# Microservice Architecture 

##  Recommended MVP Architecture

### **Optimal Service Breakdown (4 Services + Infrastructure)**

```
┌─────────────────────────────────────┐
│         API Gateway (Kong/AWS)      │
│  - Route, Rate limit, Auth check    │
└──────────────────────────────────────┘
                  │
    ┌─────────────┼─────────────────────────┬──────────────┐
    ▼             ▼             ▼            ▼              ▼
┌─────────┐ ┌──────────┐ ┌──────────────┐ ┌──────────┐ ┌──────────┐
│  Auth   │ │  User    │ │   Problem    │ │Submission│ │ Executor │
│Service  │ │ Service  │ │   Service    │ │ Service  │ │ Service  │
│         │ │          │ │ (+ TestCases)│ │          │ │          │
└─────────┘ └──────────┘ └──────────────┘ └──────────┘ └──────────┘
  │ DB:      │ DB:       │  DB:           │  DB:       │  Docker
  │ PG       │ PG        │  PG            │  PG        │  Worker
  │          │           │ (partitioned)  │ (partition)│  Pool
  
              ┌──────────────────────────────┐
              │    Message Queue (Kafka)     │
              │ - Submission execution jobs  │
              │ - Async event streaming      │
              └──────────────────────────────┘
              
              ┌──────────────────────────────┐
              │    Cache (Redis)             │
              │ - Sessions, leaderboard      │
              │ - User stats (hourly)        │
              └──────────────────────────────┘
```

---

### **Service Responsibilities**

#### **1. Auth Service**
**Responsibilities:**
- User registration (username, email, password)
- User login (credential validation)
- JWT token generation & refresh
- OAuth2 integration (GitHub, Google)
- Password reset
- Token validation middleware

**Database:** PostgreSQL (users table)

---

#### **2. User Service**
**Responsibilities:**
- User profile (bio, avatar, country, company)
- User statistics (problems solved, acceptance rate)
- User preferences (theme, notifications)
- User reputation/scores

**Database:** PostgreSQL (user profiles, stats tables)
```sql
CREATE TABLE user_profiles (
  user_id UUID PRIMARY KEY,
  bio TEXT,
  avatar_url VARCHAR(500),
  country VARCHAR(50),
  company VARCHAR(100)
);

CREATE TABLE user_stats (
  user_id UUID PRIMARY KEY,
  problems_solved INT DEFAULT 0,
  total_submissions INT DEFAULT 0,
  acceptance_rate DECIMAL(5,2) DEFAULT 0,
  reputation_score INT DEFAULT 0
);
```

**Endpoints:**
- `GET /users/me` - Current user profile
- `GET /users/{id}` - Any user's public profile
- `PUT /users/me` - Update profile
- `GET /users/{id}/stats` - User statistics

**Dependencies:**
- Auth Service (for authentication)
- Submission Service (receives events about solved problems)

---

#### **3. Problem Service** (WITH TESTCASES)
**Responsibilities:**
- Problem CRUD (create, read, update, delete)
- Test case management (attached to problems)
- Problem metadata (difficulty, acceptance rate, tags)
- Search and filtering

**Database:** PostgreSQL (problems, test_cases tables)
```sql
CREATE TABLE problems (
  problem_id SERIAL PRIMARY KEY,
  title VARCHAR(200) UNIQUE,
  description TEXT,
  difficulty VARCHAR(10),
  acceptance_rate DECIMAL(5,2),
  submission_count INT DEFAULT 0,
  solved_count INT DEFAULT 0
);

CREATE TABLE test_cases (
  test_case_id SERIAL PRIMARY KEY,
  problem_id INT REFERENCES problems(id),
  input TEXT,
  expected_output TEXT,
  is_example BOOLEAN
);
```

**Endpoints:**
- `GET /problems` - List all problems (with filters)
- `GET /problems/{id}` - Get problem + test cases
- `POST /problems` - Create problem (admin)
- `PUT /problems/{id}` - Update problem
- `DELETE /problems/{id}` - Delete problem
- `GET /problems/{id}/test-cases` - Get test cases

**Dependencies:**
- Auth Service (for admin endpoints)
- Submission Service (receives stats about submissions)

**Why keep TestCases together:**
- Always queried together = same service
- Foreign key relationships = same DB
- Single responsibility: "Manage problems and their test cases"

---

#### **4. Submission Service**
**Responsibilities:**
- Accept code submissions
- Queue submissions for execution
- Track submission status
- Store submission results
- Update problem statistics (acceptance rate, etc.)

**Database:** PostgreSQL (submissions, submission_results tables - PARTITIONED)
```sql
CREATE TABLE submissions (
  submission_id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  problem_id INT NOT NULL,
  language VARCHAR(20),
  code TEXT,
  verdict VARCHAR(20) DEFAULT 'PENDING',
  runtime_ms INT,
  memory_mb INT,
  submitted_at TIMESTAMP
) PARTITION BY RANGE (submitted_at);

CREATE TABLE submission_results (
  result_id UUID PRIMARY KEY,
  submission_id UUID,
  test_case_id INT,
  verdict VARCHAR(20),
  actual_output TEXT,
  execution_time_ms INT
);
```

**Endpoints:**
- `POST /submissions/submit` - Submit code (async)
- `GET /submissions/{id}` - Get submission status/verdict
- `GET /submissions/me` - User's recent submissions
- `GET /problems/{id}/submissions` - All submissions for problem

**Flow:**
```
1. User submits code
   ↓
2. Submission Service creates submission record (PENDING)
   ↓
3. Publishes "submission.created" event to Kafka
   ↓
4. Executor Service consumes event
   ↓
5. Executor runs code in Docker
   ↓
6. Publishes "submission.verdict_ready" event
   ↓
7. Submission Service updates record with verdict
   ↓
8. User polls or receives WebSocket notification
```

**Dependencies:**
- Auth Service (for authentication)
- Problem Service (to get test cases)
- Message Queue (to queue execution jobs)
- Executor Service (to run code)

**Why separate from Problem Service:**
- Submissions are high-traffic, write-heavy
- Need different partitioning strategy
- Can scale independently
- Logic is distinct (submission tracking vs problem management)

---

#### **5. Code Executor Service** (NOT AN API SERVICE)
**Responsibilities:**
- Consume execution jobs from queue
- Spin up Docker containers
- Run code with time/memory limits
- Capture output and errors
- Publish results back to queue

**Architecture:**
```
Worker Pool (3-5 workers running in parallel)
  │
  ├─ Worker 1: Execute job 1
  ├─ Worker 2: Execute job 2
  ├─ Worker 3: Execute job 3
  ├─ Worker 4: Idle (waiting for job)
  └─ Worker 5: Idle (waiting for job)

Message Queue (Kafka)
  │
  ├─ submission.created → [Job 1] → Executor Service
  ├─ submission.created → [Job 2] → Executor Service
  └─ submission.created → [Job 3] → Executor Service
```

**Technology:**
- Language: Java/Python
- Framework: Spring Boot (Java) or FastAPI (Python)
- Container Runtime: Docker
- Resource Limits: cgroups (CPU, memory, timeout)
- Languages Supported: Python, Java, C++, JavaScript, Go, Rust
