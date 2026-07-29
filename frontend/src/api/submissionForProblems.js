export const submissions = [
  {
    "submissionId": "7d9d4f8b-4a53-4b0d-bdf5-5e7c54e7d101",
    "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "problemId": 1,
    "language": "java",
    "verdict": "Accepted",
    "runtimeMs": 28,
    "memoryMb": 42,
    "submittedAt": "2026-07-29T09:15:23",
    "totalCases": 15,
    "failedCases": 0,
    "message": "All test cases passed."
  },
  {
    "submissionId": "4b66d0c9-1b7d-4e97-a7d3-7a3e43c19102",
    "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "problemId": 1,
    "language": "python",
    "verdict": "Wrong Answer",
    "runtimeMs": 31,
    "memoryMb": 36,
    "submittedAt": "2026-07-29T09:22:41",
    "totalCases": 15,
    "failedCases": 2,
    "message": "Failed on Test Case #8. Expected 14 but got 12."
  },
  {
    "submissionId": "8e6cbe24-9e31-4c0f-a30d-9dc67fa28103",
    "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "problemId": 1,
    "language": "java",
    "verdict": "Time Limit Exceeded",
    "runtimeMs": 2000,
    "memoryMb": 48,
    "submittedAt": "2026-07-29T09:35:17",
    "totalCases": 15,
    "failedCases": 5,
    "message": "Execution exceeded the time limit on Test Case #10."
  },
  {
    "submissionId": "cf79c3b5-8c46-4c34-9c5d-5b9bb3a4b104",
    "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "problemId": 1,
    "language": "python",
    "verdict": "Runtime Error",
    "runtimeMs": 12,
    "memoryMb": 34,
    "submittedAt": "2026-07-29T09:48:56",
    "totalCases": 15,
    "failedCases": 15,
    "message": "IndexError: list index out of range"
  },
  {
    "submissionId": "f6d56ef2-c8f8-4631-9fb5-4c9c2b2ef105",
    "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "problemId": 1,
    "language": "java",
    "verdict": "Compilation Error",
    "runtimeMs": 0,
    "memoryMb": 0,
    "submittedAt": "2026-07-29T10:02:11",
    "totalCases": 0,
    "failedCases": 0,
    "message": "Cannot find symbol: variable nums"
  }
]

export const getSubmissionsForProblem = async (problemId) => {
    return submissions;
}