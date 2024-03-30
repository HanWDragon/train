import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    {
        path: '/',
        name: 'main',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('../views/MainView.vue'),

        children: [
            {
                path: 'welcome',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import('../views/MainComponent/MainViewWelcome.vue')
            },
            {
                path: 'about',
                component: () => import('../views/MainComponent/MainViewAbout.vue')
            },
            {
                path: 'station',
                component: () => import('../views/MainComponent/Station.vue')
            }
        ]
    },
    {
        path: '',
        redirect: '/welcome'
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router

