import createAxios from "./createAxios"

const userUrl = 'http://localhost:8888/powerbank/user'
const locationUrl = 'http://localhost:8888/powerbank/point'
const batteryUrl = 'http://localhost:8888/powerbank/pal'
const payUrl = 'http://localhost:8888/powerbank/order'
const uploadUrl = 'http://localhost:8888/powerbank/img/'

// 用户 以及登录
const login = (value)=>createAxios("post",userUrl+"/login?from=backend",value)
const easyLogin =(value)=>createAxios("post",userUrl+"/login/np?from=backend",value)
const addUser = (value)=>createAxios("post",userUrl,value);
const checkUsername = (value)=>createAxios("get",userUrl+'/check/'+value.username+`?userId=${value.userId}`)
const deleteUser = (value)=>createAxios("delete",userUrl+`/${value.userId}`);
const changeUser = (value)=>createAxios("put",userUrl,value);
const getUsers = (value)=>createAxios("get",userUrl,value);
//角色


// 投放点
const addLocation = (value)=>createAxios("post",locationUrl,value)
const deleteLocation = (value)=>createAxios("delete",locationUrl+`/${value.dpId}`)
const changeLocation =(value)=>createAxios("put",locationUrl,value)
const getLocation = (value)=>createAxios("get",locationUrl,value)

// 充电宝
const addBattery = (value)=>createAxios("post",batteryUrl,value)
const deleteBattery = (value)=>createAxios("delete",batteryUrl+`/${value.id}`)
const changeBattery =(value)=>createAxios("put",batteryUrl,value)
const getBattery = (value)=>createAxios("get",batteryUrl,value)
const searchBattery=(value)=>createAxios("get",batteryUrl,value)
const getAllLication = ()=>createAxios("get",locationUrl)
// 订单
const deletePay = (value)=>createAxios("delete",payUrl+`${value.orderId}`)
const getPays =(value)=>createAxios("get",payUrl,value)
const searchPays = (value)=>createAxios("get",payUrl,value)
const searchPaysByStatus =()=>createAxios("get",payUrl,value)
export {
    login,easyLogin,addUser,deleteUser,changeUser,getUsers,checkUsername,
    addLocation,deleteLocation,changeLocation,getLocation,
    addBattery,deleteBattery,changeBattery,searchBattery,getBattery,getAllLication,
    getPays,searchPays,deletePay,searchPaysByStatus,
    uploadUrl
}