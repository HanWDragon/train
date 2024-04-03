import {createRouter, createWebHistory} from 'vue-router'


const routes = [
    {
        path: '/',
        name: 'main',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import('@/views/main/MainView.vue'),

        children: [
            {
                path: 'welcome',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import('@/views/main/MainViewWelcome.vue')
            },
            {
                path: 'about',
                component: () => import('@/views/main/MainViewAbout.vue')
            },
            {
                path: 'base/',
                children: [
                    {
                        path: 'station',
                        component: () => import('../views/base/Station.vue')
                    },
                    {
                        path: 'train',
                        component: () => import('../views/base/Train.vue')
                    },
                    {
                        path: 'train-station',
                        component: () => import('../views/base/TrainStation.vue')
                    },
                    {
                        path: 'train-carriage',
                        component: () => import('../views/base/TrainCarriage.vue')
                    },
                    {
                        path: 'train-seat',
                        component: () => import('../views/base/TrainSeat.vue')
                    },
                ]
            },
            {
                path: 'business/',
                children: [
                    {
                        path: 'daily-train',
                        component: () => import('../views/business/DailyTrain.vue')
                    }
                ]
            },
            {
                path: 'batch/',
                children: [
                    {
                        path: 'job',
                        name: 'job',
                        component: () => import('../views/batch/Job.vue')
                    }
                ]
            },
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


