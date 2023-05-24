import {getSession} from "@/API/session"
const state ={
    username:getSession("username")||"",
    balance:getSession("balance")||0,
    avatar:getSession("avatar")||"",
    phone:getSession("phone")||"",
    userId:getSession("userId")||"",
    zone:getSession("zone")||""
}
export default state