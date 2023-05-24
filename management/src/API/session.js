const setSession =(key,data)=>{
    localStorage.setItem(key,JSON.stringify(data))
}
const getSession =(key)=>{
    
return JSON.parse(localStorage.getItem(key))
}
const removeSession =(key)=>{
    localStorage.removeItem(key)
}
export {
    setSession,
    getSession,
    removeSession
}