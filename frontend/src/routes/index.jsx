import { createBrowserRouter, Navigate } from 'react-router-dom'
import MainLayout from '../components/layouts/MainLayout'
import ProtectedRoute from '../components/common/ProtectedRoute'
import LoginPage from '../features/auth/LoginPage'
import RegisterPage from '../features/auth/RegisterPage'
import ProblemListPage from '../features/problems/ProblemListPage'
import ProblemDetailPage from '../features/problems/ProblemDetailPage'
import EditorPage from '../features/editor/EditorPage'
import SubmissionPage from '../features/submission/SubmissionPage'
import ProfilePage from '../features/user/ProfilePage'

const router = createBrowserRouter([
  {
    path: '/',
    element: <Navigate to="/problems" replace />,
  },
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/register',
    element: <RegisterPage />,
  },
  {
    element: <ProtectedRoute />,
    children: [
      {
        element: <MainLayout />,
        children: [
          {
            path: '/problems',
            element: <ProblemListPage />,
          },
          {
            path: '/problems/:id',
            element: <ProblemDetailPage />,
          },
          {
            path: '/editor',
            element: <EditorPage />,
          },
          {
            path: '/submissions',
            element: <SubmissionPage />,
          },
          {
            path: '/profile',
            element: <ProfilePage />,
          },
        ],
      },
    ],
  },
])

export default router
