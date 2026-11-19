import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Strategys from '../components/Strategys.vue';
import StrategyDetail from '../components/StrategyDetail.vue';
import StrategyParameters from '../components/StrategyParameters.vue';
import StrategyParameterDetail from '../components/StrategyParameterDetail.vue';
import StrategyInstruments from '../components/StrategyInstruments.vue';
import StrategyInstrumentDetail from '../components/StrategyInstrumentDetail.vue';
import StrategyVersions from '../components/StrategyVersions.vue';
import StrategyVersionDetail from '../components/StrategyVersionDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/strategys',
    name: 'Strategys',
    component: Strategys
  },
  {
    path: '/strategy/:id',
    name: 'StrategyDetail',
    component: StrategyDetail,
    props: true
  },
  {
    path: '/strategyParameters',
    name: 'StrategyParameters',
    component: StrategyParameters
  },
  {
    path: '/strategyParameter/:id',
    name: 'StrategyParameterDetail',
    component: StrategyParameterDetail,
    props: true
  },
  {
    path: '/strategyInstruments',
    name: 'StrategyInstruments',
    component: StrategyInstruments
  },
  {
    path: '/strategyInstrument/:id',
    name: 'StrategyInstrumentDetail',
    component: StrategyInstrumentDetail,
    props: true
  },
  {
    path: '/strategyVersions',
    name: 'StrategyVersions',
    component: StrategyVersions
  },
  {
    path: '/strategyVersion/:id',
    name: 'StrategyVersionDetail',
    component: StrategyVersionDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
