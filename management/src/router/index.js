import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import HomePage from "../views/HomePage"
import Location from "../views/Location"
import Battery from "../views/Battery"
import Pay from "../views/Pay"
import Role from "../views/User/Role"
import User from "../views/User/UserManagement"
import AddLocation from "../components/Form/Location.vue"
import AddBattery from "../components/Form/Battery.vue"
import AddUser from "../components/Form/User.vue"
import Login from "../views/Login.vue"
const routes = [
  {
    path: '/',
  },{
    path:"/login",
    name:"login",
    component:Login
  }, {
    path: "/home",
    name: "home",
    component: HomeView,
    children: [
      {
        path: "page",
        name: "page",
        component: HomePage
      },
      {
        path: "location",
        name: "location",
        component: Location
      },
      {
        path: "battery",
        name: "battery",
        component: Battery
      },{
        path:"pay",
        name:"pay",
        component:Pay
      },
      {
        path: "role",
        name: "role",
        component: Role
      },
      {
        path: "user",
        name: "user",
        component: User
      },{
        path:"addBattery",
        name:"addBattery",
        component:AddBattery
      },{
        path:"addLocation",
        name:"addLocation",
        component:AddLocation
      },{
        path:"addUser",
        name:"addUser",
        component:AddUser
      }
    ]
  }, {

  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
