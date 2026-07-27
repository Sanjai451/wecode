## Folder structure

```
leetcode-frontend/
├── public/
│   ├── index.html
│   ├── favicon.ico
│   └── robots.txt
│
├── src/
│   ├── index.tsx                      # Entry point
│   ├── App.tsx                        # Root component
│   ├── App.css
│   │
│   ├── assets/                        # Static assets
│   │   ├── images/
│   │   ├── icons/
│   │   ├── fonts/
│   │   └── styles/
│   │       ├── globals.css
│   │       ├── variables.css
│   │       └── animations.css
│   │
│   ├── api/                           # API communication layer
│   │   ├── client.ts                  # Axios/Fetch configuration
│   │   ├── auth.ts                    # Auth API endpoints
│   │   ├── user.ts                    # User API endpoints
│   │   ├── problem.ts                 # Problem API endpoints
│   │   ├── submission.ts              # Submission API endpoints
│   │   └── types.ts                   # API response types
│   │
│   ├── hooks/                         # Custom React hooks
│   │   ├── useAuth.ts                 # Auth logic
│   │   ├── useUser.ts                 # User data fetching
│   │
│   ├── context/                       # Context API for global state
│   │   ├── AuthContext.tsx            # Auth state
│   │   ├── ThemeContext.tsx           # Theme (light/dark)
│   ├── utils/                         # Utility functions
│   │   ├── formatters.ts              # Format dates, times, 
│   │   └── helpers.ts                 # General helpers
│   │
│   ├── middleware/                    # Route middleware
│   │   ├── ProtectedRoute.tsx         # Auth-protected routes
│   │   ├── AdminRoute.tsx             # Admin-only routes
│   │   └── ErrorBoundary.tsx          # Error handling
│   │
│   ├── features/                      # Feature-based organization (RECOMMENDED)
│   │   │
│   │   ├── auth/                      # Authentication feature
│   │   │   ├── components/
│   │   │   │   ├── LoginForm.tsx
│   │   │   │   ├── RegisterForm.tsx
│   │   │   │   ├── OAuthButtons.tsx
│   │   │   │   └── ForgotPassword.tsx
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.tsx
│   │   │   │   └── RegisterPage.tsx
│   │   │   ├── services/
│   │   │   │   └── authService.ts
│   │   │   ├── hooks/
│   │   │   │   └── useAuthForm.ts
│   │   │   └── types.ts
│   │   │
│   │   ├── problems/                  # Problems feature
│   │   │   ├── components/
│   │   │   │   ├── ProblemList.tsx
│   │   │   │   ├── ProblemCard.tsx
│   │   │   │   ├── ProblemFilters.tsx
│   │   │   │   ├── ProblemSearch.tsx
│   │   │   │   ├── DifficultyBadge.tsx
│   │   │   │   └── ProblemDetailView.tsx
│   │   │   ├── pages/
│   │   │   │   ├── ProblemsPage.tsx
│   │   │   │   └── ProblemDetailPage.tsx
│   │   │   ├── services/
│   │   │   │   └── problemService.ts
│   │   │   ├── hooks/
│   │   │   │   ├── useProblems.ts
│   │   │   │   ├── useProblemFilters.ts
│   │   │   │   └── useProblemSearch.ts
│   │   │   └── types.ts
│   │   │
│   │   ├── editor/                    # Code editor feature
│   │   │   ├── components/
│   │   │   │   ├── CodeEditor.tsx
│   │   │   │   ├── EditorHeader.tsx
│   │   │   │   ├── LanguageSelector.tsx
│   │   │   │   ├── TestCasesPanel.tsx
│   │   │   │   ├── TestCaseItem.tsx
│   │   │   │   ├── ConsoleOutput.tsx
│   │   │   │   └── EditorTabs.tsx
│   │   │   ├── pages/
│   │   │   │   └── EditorPage.tsx
│   │   │   ├── services/
│   │   │   │   └── editorService.ts
│   │   │   ├── hooks/
│   │   │   │   ├── useCodeEditor.ts
│   │   │   │   └── useLanguageConfig.ts
│   │   │   ├── utils/
│   │   │   │   └── editorTheme.ts
│   │   │   └── types.ts
│   │   │
│   │   ├── submission/                # Submission feature
│   │   │   ├── components/
│   │   │   │   ├── SubmitButton.tsx
│   │   │   │   ├── SubmissionStatus.tsx
│   │   │   │   ├── SubmissionHistory.tsx
│   │   │   │   ├── SubmissionItem.tsx
│   │   │   │   ├── VerdictBadge.tsx
│   │   │   │   └── SubmissionDetails.tsx
│   │   │   ├── pages/
│   │   │   │   └── SubmissionPage.tsx
│   │   │   ├── services/
│   │   │   │   └── submissionService.ts
│   │   │   ├── hooks/
│   │   │   │   ├── useSubmit.ts
│   │   │   │   ├── useSubmissionPolling.ts
│   │   │   │   └── useWebSocketVerdicts.ts
│   │   │   ├── store/
│   │   │   │   └── submissionSlice.ts
│   │   │   └── types.ts
│   │   │
│   │   ├── user/                      # User profile feature
│   │   │   ├── components/
│   │   │   │   ├── UserProfile.tsx
│   │   │   │   ├── UserStats.tsx
│   │   │   │   ├── UserSolvedProblems.tsx
│   │   │   │   ├── UserSubmissionHistory.tsx
│   │   │   │   ├── EditProfileModal.tsx
│   │   │   │   └── ChangePasswordModal.tsx
│   │   │   ├── pages/
│   │   │   │   ├── ProfilePage.tsx
│   │   │   │   └── SettingsPage.tsx
│   │   │   ├── services/
│   │   │   │   └── userService.ts
│   │   │   ├── hooks/
│   │   │   │   ├── useUserProfile.ts
│   │   │   │   └── useUserStats.ts
│   │   │   └── types.ts
│   │   │
│   │
│   ├── components/                    # Shared/Common components
│   │   ├── layout/
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   ├── Footer.tsx
│   │   │   ├── MainLayout.tsx
│   │   │   └── Navbar.tsx
│   │   │
│   │   ├── ui/                        # Reusable UI components
│   │   │   ├── Button/
│   │   │   │   ├── Button.tsx
│   │   │   │   └── Button.module.css
│   │   │   ├── Modal/
│   │   │   │   ├── Modal.tsx
│   │   │   │   └── Modal.module.css
│   │   │   ├── Input/
│   │   │   │   ├── Input.tsx
│   │   │   │   └── Input.module.css
│   │   │   ├── Card/
│   │   │   │   ├── Card.tsx
│   │   │   │   └── Card.module.css
│   │   │   ├── Badge/
│   │   │   │   ├── Badge.tsx
│   │   │   │   └── Badge.module.css
│   │   │   ├── Tabs/
│   │   │   │   ├── Tabs.tsx
│   │   │   │   └── Tabs.module.css
│   │   │   ├── Dropdown/
│   │   │   │   ├── Dropdown.tsx
│   │   │   │   └── Dropdown.module.css
│   │   │   ├── Pagination/
│   │   │   │   ├── Pagination.tsx
│   │   │   │   └── Pagination.module.css
│   │   │   ├── Loading/
│   │   │   │   ├── Spinner.tsx
│   │   │   │   ├── Skeleton.tsx
│   │   │   │   └── Loading.module.css
│   │   │   ├── Toast/
│   │   │   │   ├── Toast.tsx
│   │   │   │   └── Toast.module.css
│   │   │   └── Error/
│   │   │       ├── ErrorMessage.tsx
│   │   │       └── Error.module.css
│   │   │
│   │   ├── forms/                     # Reusable form components
│   │   │   ├── FormInput.tsx
│   │   │   ├── FormSelect.tsx
│   │   │   ├── FormCheckbox.tsx
│   │   │   └── FormError.tsx
│   │   │
│   │   ├── common/                    # Other common components
│   │   │   ├── NotFound.tsx
│   │   │   ├── Unauthorized.tsx
│   │   │   ├── LoadingScreen.tsx
│   │   │   └── ConfirmDialog.tsx
│   │
│   ├── routes/                        # Routing configuration
│   │   ├── index.tsx                  # Route definitions
│   │   ├── RouteConfig.ts             # Route configuration
│   │   └── routePaths.ts              # Route path constants
│   │
│   │
│   └── config/                        # Configuration files
│       ├── api.config.ts              # API configuration
│       ├── app.config.ts              # App configuration
│       └── env.ts                     # Environment variables
│
├── .env                               # Environment variables
├── .env.example                       # Example env file
├── .gitignore
├── package.json
├── tsconfig.json
├── tailwind.config.js (if using Tailwind)
├── vite.config.ts (if using Vite)
└── README.md

```