import createAxios from "./createAxios"

const userUrl = 'http://localhost:8888/powerbank/user'
const locationUrl = 'http://localhost:8888/powerbank/point'
const batteryUrl = 'http://localhost:8888/powerbank/pal'
const payUrl = 'http://localhost:8888/powerbank/order'
const uploadUrl = 'http://localhost:8888/powerbank/img/'

// 用户 以及登录
const login = (value)=>createAxios("post",userUrl+"/login",value)
const easyLogin =(value)=>createAxios("post",userUrl+"/login/np",value)
const addUser = (value)=>createAxios("post",userUrl+"/forward",value);
const checkUsername = (value)=>createAxios("get",userUrl+'/check/'+value.username)
const changeUser = (value)=>createAxios("put",userUrl,value);
const getUsers = (value)=>createAxios("get",userUrl,value);
const changePassword = (value)=>createAxios("put",userUrl+"/pwd",value)

// 投放点
const getLocation = (value)=>createAxios("get",locationUrl+'/forward',value)

// 充电宝
const getBattery = (value)=>createAxios("get",batteryUrl+"/forward",value)
// 订单
const addPay = (value)=>createAxios("post",payUrl,value)
const getCost = (value) =>createAxios("get",payUrl+"/cost",value)
const finshPay = (value) =>createAxios("put",payUrl,value)
const getPays =(value)=>createAxios("get",payUrl,value)

export {
    login,easyLogin,addUser,changeUser,getUsers,checkUsername,changePassword,
    getLocation,
    getBattery,
    getPays,addPay,getCost,finshPay,
    uploadUrl
}