import {setSession} from "@/API/session"
const mutations ={
      setActive(state,active){
        state.active = active
        setSession("active",active)
      },
      setPageTitle(state,pageTitle){
        state.pageTitle = pageTitle
        setSession("pageTitle",pageTitle)
      },
      setSubPageTitle(state,pageTitle){
        state.subPageTitle = pageTitle
        setSession("subPageTitle",pageTitle)
      }
}
export default mutations