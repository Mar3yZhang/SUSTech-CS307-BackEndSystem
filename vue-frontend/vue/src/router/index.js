import { createRouter, createWebHistory } from 'vue-router'
import head from "@/components/head";
import Home from "@/views/Home";
import Aside from "@/components/Aside";

const routes = [
    // path: '/about',
    // name: 'about',
    // // route level code-splitting
    // // this generates a separate chunk (about.[hash].js) for this route
    // // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: 'staff',
        name: 'Staff',
        component: () => import("@/views/Staff")
      },
      {
        path: 'enterprise',
        name: 'Enterprise',
        component: () => import('@/views/Enterprise')
      },
      {
        path: 'login',
        name: 'Login',
        component: () => import("@/views/Login")
      },
      {
        path: 'create',
        name: 'Create',
        component: () => import("@/views/Create")
      },
    ]
    // redirect: '/login',

  },
//   {
//     path: '/',
//     name: 'Home',
//     component:Home
// }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
