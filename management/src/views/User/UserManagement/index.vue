<template>
  <div>
    <!-- 标题处不写 -->
    <a-card>
      <template #title>
        <a-select ref="select" v-model:value="searchOption" style="width: 120px" @focus="focus" @change="handleChange">
          <a-select-option value="全部">全部</a-select-option>
          <a-select-option value="普通用户">普通用户</a-select-option>
          <a-select-option value="管理员">管理员</a-select-option>
        </a-select>
        <a-input-search style="width: 50%;" v-model:value="value" placeholder="搜索用户信息" enter-button="搜索" size="default"
          @search="onSearch" />
      </template>
      <template #extra>
        <a-button type="primary" block @click="addUser">添加用户</a-button>
      </template>
      <a-table :data-source="data" @change="changeTable" :pagination="pagination">

        <a-table-column key="key" title="编号" data-index="key" align="center" />
        <a-table-column key="username" title="用户名" data-index="username" align="center" />
        <a-table-column key="password" title="登录密码" data-index="password" align="center" />
        <a-table-column key="phone" title="电话号码" data-index="phone" align="center" />
        <a-table-column key="balance" title="账户余额" data-index="balance" align="center" />

        <a-table-column key="role" title="角色身份" data-index="role" align="center">
          <template #default="{ text: role }">
            <div>
              {{ role === 1 ? "管理员" : "普通用户" }}
            </div>
          </template>
        </a-table-column>

        <a-table-column key="avatar" title="图片" data-index="avatar" align="center">
          <template #default="{ text: src }">
            <a-image :width="50" :height="50" style="margin-top: -16px;" :preview="true" :src="src" />
            <!-- <img src="@/assets/img/avatar.jpg" width="50" height="50" alt=""> -->
          </template>
        </a-table-column>

        <a-table-column key="key" title="操作" data-index="key" align="center">
          <template #default="{ text: key }">
            <span>
              <a-tag color="blue" @click="changeItem(key)">修改</a-tag>
              <a-popconfirm title="确定删除?" ok-text="确定" cancel-text="取消" @confirm="deleteItem(key)" @cancel="cancel">
                <a-tag color="red">删除</a-tag>
              </a-popconfirm>
            </span>
          </template>
        </a-table-column>

      </a-table>

    </a-card>
    <br />

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from "vue-router"
import { getUsers, deleteUser } from "@/API/url"
import { uploadUrl } from "@/API/url"
const router = useRouter()
const value = ref('');
const searchOption = ref('全部')

// {
//     key: 1,
//     userId: "U123",
//     username: "一枫无",
//     password: "159632dsa",
//     phone: "18218102741",
//     balance: 156.5,
//     role: "管理员",
//     avatar: "https://img.wxcha.com/m00/e2/d8/7b715d6aeee61f9cf108b10feec0a73f.jpg"
//   }
const data = ref([

])
// 分页
const pagination = ref({
  current: 1,
  hideOnSinglePage: true,
  pageSize: 10,
  total: 1
})
//搜索
const onSearch = async (searchValue) => {
  pagination.value.current = 1
  getData("search", searchValue)
}
// 选择
const handleChange = () => {
  pagination.value.current = 1
  getData("init")
}
// 分页
let changeTable = (page) => {
  pagination.value = page
  if(value.value === ""){
    getData("init")
  }else{
    getData("search",value.value)
  }

}
//修改
const changeItem = (value) => {
  router.push({ name: "addUser", query: data.value[value] })
}
// 删除
const deleteItem = (value) => {
  deleteUser({ userId: data.value[value].userId }).then(() => {
    getData("init")
  })
}
//添加
const addUser = () => {
  router.push("addUser")
}
// 刷新数据
const getData = async (method, Value) => {
  let res = null
  let role = ""
  if (searchOption.value === "普通用户") {
    role = 0
  } else if (searchOption.value === "管理员") {
    role = 1
  }
  if (method === "init") {
    res = await getUsers({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize,
      role: role
    })

  } else if (method === "search") {
    res = await getUsers({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize,
      username: Value,
      role: role
    })
  }
  if(res.code == 200){
    pagination.value.total = res.data.total
    data.value = res.data.list.map((item, index) => {
      item.avatar = uploadUrl + item.avatar
      item.key = index;
      return item
    })
  }
}

onMounted(() => {
  getData("init")
})
</script>


<style>

</style>
