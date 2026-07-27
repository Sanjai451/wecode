import { createBrowserRouter, Navigate } from 'react-router-dom'
import MainLayout from '../components/MainLayout'
import ProtectedRoute from '../components/ProtectedRoute'
import LoginPage from '../pages/LoginPage'
import RegisterPage from '../pages/RegisterPage'
import ProblemListPage from '../pages/ProblemListPage'
import ProblemDetailPage from '../pages/ProblemDetailPage'
import EditorPage from '../pages/EditorPage'
import SubmissionPage from '../pages/SubmissionPage'
import ProfilePage from '../pages/ProfilePage'

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
