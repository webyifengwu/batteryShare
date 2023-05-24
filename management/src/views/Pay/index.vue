<template>
  <div>
    <!-- 标题处不写 -->
    <a-card>
      <template #title>
        <div>
          <a-select ref="select" v-model:value="statusOption" style="width: 120px" @focus="focus" @change="statusChange">
            <a-select-option value="订单状态">订单状态</a-select-option>
            <a-select-option value="当前订单">当前订单</a-select-option>
            <a-select-option value="历史订单">历史订单</a-select-option>
          </a-select>
          <a-input-search v-model:value="value" style="width:50%" placeholder="搜索订单信息" enter-button="搜索" size="default"
            @search="onSearch" />
        </div>
      </template>

      <a-table :data-source="data" @change="changeTable" :pagination="pagination">

        <a-table-column key="key" title="编号" data-index="key" align="center" />
        <a-table-column key="orderId" title="订单号" data-index="orderId" align="center" />
        <!-- <a-table-column key="pId" title="充电宝id" data-index="pId" align="center" /> -->
        <a-table-column key="username" title="用户" data-index="username" align="center" />
        <a-table-column key="dpName" title="投放点" data-index="dpName" align="center" />
        <a-table-column key="time" title="使用时长/小时" data-index="time" align="center" />
        <a-table-column key="total" title="总金额" data-index="total" align="center" />

        <a-table-column key="status" title="租借状态" data-index="status" align="center">
          <template #default="{ text: status }">
            <div>
              {{ status === 1 ? "当前订单" : "历史订单" }}
            </div>
          </template>
        </a-table-column>
        <a-table-column key="createTime" title="订单开始时间" data-index="createTime" align="center" />
        <a-table-column key="endTime" title="订单结束时间" data-index="endTime" align="center" />

        <!-- <a-table-column key="key" title="操作" data-index="key" align="center">
          <template #default="{ text: id }">
            <span>
              <a-popconfirm title="确定删除?" ok-text="确定" cancel-text="取消" @confirm="deleteItem(id)" @cancel="cancel">
                <a-tag color="red">删除</a-tag>
              </a-popconfirm>
            </span>
          </template>
        </a-table-column> -->

      </a-table>

    </a-card>
    <br />

  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { getPays, searchPays, deletePay } from "@/API/url"
const value = ref('');
const statusOption = ref("订单状态")
const onSearch = searchValue => {
  getData("search",searchValue)

}
// {
//     key: '1',
//     orderId: "d90",
//     pId: "d304",
//     username: "U358",
//     dpName: "L356",
//     time: 10,
//     total: 15.6,
//     status: 2,
//     createTime:"",
//     endTime:""
//   }
//表单数据
const data = ref([

]);
const pagination = ref({
  current: 1,
  hideOnSinglePage: true,
  pageSize: 10,
  total: 1
})

//函数
const deleteItem = (value) => {
  deletePay(data.value[value]).then(() => {
    getData("init")
  })
}

// 选择
const statusChange = () => {
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

//刷新数据
const getData = async (method, Value) => {

  let res = {}
  let status = null

  if (statusOption.value === "当前订单") {
    status = 1
  } else if (statusOption.value === "历史订单") {
    status = 0
  } else {
    status = null
  }
  if (method === "init") {
    res = await getPays({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize,
      status: status
    })
  } else if (method === "search") {
    res = await getPays({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize,
      status: status,
      username: Value
    })
  }
if(res.code === 200){
  pagination._value.total = res.data.total
  data.value = res.data.list.map((item, index) => {
    item.key = index;
    return item
  })
}

}

onMounted(() => {
  getData("init")
})
</script>


<style></style>
