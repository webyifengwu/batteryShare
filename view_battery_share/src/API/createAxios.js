import axios from "axios"
import { getSession } from "@/API/session"
//请求拦截
axios.interceptors.request.use(
    function (config) {
        const token = getSession("token")
        if (token) {
            config.headers['Authorization'] = token
        }
        return config;
    },
    function (error) {
        // 对请求错误做些什么
        return Promise.reject(error);
    });

async function createAxios(type, url, data) {
    if (type.toLocaleLowerCase() === 'get') {
        return await new Promise((res, rej) => {
            getAxios(url, data, res, rej)
        })
    } else if (type.toLocaleLowerCase() === 'put') {
        return await new Promise((res, rej) => {
            putAxios(url, data, res, rej)
        })
    } else if (type.toLocaleLowerCase() === 'delete') {
        return await new Promise((res, rej) => {
            deleteAxios(url, data, res, rej)
        })
    } else {
        return await new Promise((res, rej) => {
            postAxios(url, data, res, rej)
        })
    }
}

// get 获取
function getAxios(url, params, res, rej) {

    axios.get(url, {
        params
    }).then(({ data }) => {
        res(data)
    }).catch((err) => {
        rej(err)
    })
}

// post 获取
function postAxios(url, data = {}, res, rej) {
    axios.post(url, data).then(({ data }) => {
        res(data)
    }).catch((err) => {
        rej(err)
    })
}
// delete 获取
function deleteAxios(url, data = {}, res, rej) {
    axios.delete(url, data).then(({ data }) => {
        res(data)
    }).catch((err) => {
        rej(err)
    })
}
// put 获取
function putAxios(url, data = {}, res, rej) {
    axios.put(url, data).then(({ data }) => {
        res(data)
    }).catch((err) => {
        rej(err)
    })
}



// 带有 异步请求的需要这样导出 不然会报错
export default createAxios