import Vue from 'vue'
import Router from 'vue-router'
import Car from '@/components/Car'
import CarAdd from '@/components/CarAdd'
import CarUpdate from '@/components/CarUpdate'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'car',
      component: Car
    }, {
      path: '/car/add',
      name: 'carAdd',
      component: CarAdd
    }, {
      path: '/car/update',
      name: 'carUpdate',
      component: CarUpdate
    }
  ]
})
