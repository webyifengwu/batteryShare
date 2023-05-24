import { createStore } from 'vuex'
import pageModule from './pageModule'
import userModule from './userModel'
export default createStore({
  modules: {
    pageModule,
    userModule
  }
})
