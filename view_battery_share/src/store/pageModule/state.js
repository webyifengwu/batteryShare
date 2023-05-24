import {getSession} from "@/API/session"
const state ={
    active:getSession("active")||0,
    pageTitle:getSession("pageTitle")||"首页",
    subPageTitle:getSession("pageTitle")||""
    // pageTitle:"首页"
}
export default state