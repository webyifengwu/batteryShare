import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home.vue"
import Page from '@/views/Home/Page/Page.vue'
import Setting from "@/views/Home/Setting/Setting.vue"
import Login from "@/views/Login.vue"
import Register from "@/views/Register.vue"
import Back from "@/views/Home/Back/Back.vue"
import Message from "@/views/Message.vue"
import Location from "@/views/Message/Location"
import Rule from "@/views/Message/Rule"
import History from "@/views/Message/History"
import Battery from "@/views/Message/Battery"
import Recharge from "@/views/Message/Recharge"
import About from "@/views/Message/About"
import Term from "@/views/Message/Term"
import Return from "@/views/Message/Return"
import Details from "@/views/Message/Details"
import Person from "@/views/Message/Person"
import ChangeUsername from "@/views/Message/Person/ChangeUsername"
import ChangeAvatar from "@/views/Message/Person/ChangeAvatar"
import ChangePassword from "@/views/Message/Person/ChangePassword"
import ChangePhone from "@/views/Message/Person/ChangePhone"

const routes = [
  {
    path: "/login",
    name: "login",
    component: Login
  }, 
  {
    path: "/register",
    name: "register",
    component: Register
  },
   {
    path: "/home",
    name: "home",
    component: Home,
    children: [{
      path: "page",
      name: "page",
      component: Page
    }, {
      path: "setting",
      name: "setting",
      component: Setting
    }, {
      path: "back",
      name: "back",
      component: Back
    }]

  },
   {
    path: "/message",
    name: "message",
    component: Message,
    children: [
      {
        path: 'location',
        name: "location",
        component: Location
      }, {
        path: "rule",
        name: "rule",
        component: Rule
      }, {
        path: "history",
        name: "history",
        component: History
      },{
        path:"battery",
        name:"battery",
        component:Battery
      },{
        path:"recharge",
        name:"recharge",
        component:Recharge
      },{
        path:"person",
        name:"person",
        component:Person,
      },{
        path:"about",
        name:'about',
        component:About
      },{
        path:"term",
        name:"term",
        component:Term
      },{
        path:"return",
        name:"return",
        component:Return
      },{
        path:"details",
        name:"details",
        component:Details
      },{
        path:"avatar",
        name:"avatar",
        component:ChangeAvatar
      },
      {
        path:"username",
        name:"username",
        component:ChangeUsername
      },
      {
        path:"phone",
        name:"phone",
        component:ChangePhone
      },
      {
        path:"password",
        name:"password",
        component:ChangePassword
      }
    ]
  }
]






const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
