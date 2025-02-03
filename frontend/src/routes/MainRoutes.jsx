import { lazy } from 'react';
import { Navigate } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import Loadable from 'ui-component/Loadable';
import MainLayout from 'layout/MainLayout';
import PrivateRoute from './PrivateRoute';

// Lazy-loaded components
const AuthLogin3 = Loadable(lazy(() => import('views/pages/authentication3/Login3')));
const DashboardDefault = Loadable(lazy(() => import('views/dashboard')));
const UtilsTypography = Loadable(lazy(() => import('views/utilities/Typography')));
const UtilsColor = Loadable(lazy(() => import('views/utilities/Color')));
const UtilsShadow = Loadable(lazy(() => import('views/utilities/Shadow')));
const SamplePage = Loadable(lazy(() => import('views/sample-page')));
const JobsAll = Loadable(lazy(() => import('views/jobs/JobsAll')));

const MainRoutes = [
  // Public route for login
  {
    path: '/login',
    element: <AuthLogin3 />
  },
  // Protected routes
  {
    path: '/',
    element: (
      <PrivateRoute>
        <MainLayout />
      </PrivateRoute>
    ),
    children: [
      // When user goes to "/" redirect to dashboard
      {
        path: '/',
        element: <Navigate to="/dashboard" replace />
      },
      {
        path: 'dashboard',
        element: <DashboardDefault />
      },
     {
      path: 'jobs/all-jobs',
      element: <JobsAll />
    },
      {
        path: 'utils/util-typography',
        element: <UtilsTypography />
      },
      {
        path: 'utils/util-color',
        element: <UtilsColor />
      },
      {
        path: 'utils/util-shadow',
        element: <UtilsShadow />
      },
      {
        path: 'sample-page',
        element: <SamplePage />
      }
    ]
  },
  // Fallback route: if no routes match, redirect to /login
  {
    path: '*',
    element: <Navigate to="/login" replace />
  }
];

export default MainRoutes;