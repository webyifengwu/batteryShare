<template>
  <div>
    <!-- 标题处不写 -->
    <a-card>
      <template #title>
        <a-select ref="select" v-model:value="searchOption" style="width: 175px" @focus="focus" @change="handleChange">
          <a-select-option value="投放点">通过投放点名搜索</a-select-option>
          <a-select-option value="区域">通过区域搜索</a-select-option>
        </a-select>
        <a-input-search v-model:value="value" style="width:50%" placeholder="搜索投放点信息" enter-button="搜索" size="default"
          @search="onSearch" />
      </template>
      <template #extra>
        <!-- <a href="#">more</a> -->
        <a-button type="primary" block @click="addLocation">添加投放点</a-button>
      </template>

      <!-- <p>card content</p>
      <p>card content</p>
      <p>card content</p> -->

      <a-table :data-source="data" @change="changeTable" :pagination="pagination">

        <a-table-column key="key" title="序号" data-index="key" align="center" />
        <a-table-column key="dpName" title="投放点名" data-index="dpName" align="center" />
        <a-table-column key="dpZone" title="所属区域" data-index="dpZone" align="center" />
        <a-table-column key="dpStock" title="现有充电宝数/块" data-index="dpStock" align="center" />
        <a-table-column key="dpAll" title="总数/块" data-index="dpAll" align="center" />
        <a-table-column key="vacancies" title="空位/块" data-index="vacancies" align="center" />
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
import { onMounted, ref } from 'vue';
import { useRouter } from "vue-router"
import { getLocation, deleteLocation } from "@/API/url"
const router = useRouter()
const value = ref('');
const searchOption = ref('投放点')

// {
//     key: 1,
//     dpId: "d90",
//     dpName:"微微快充吧",
//     dpStock: 5,
//     dpZone:"a123",
//     dpAll:10,
//     vacancies:5,
//   }
const data = ref([


]);
const pagination = ref({
  current: 1,
  hideOnSinglePage: true,
  pageSize: 10,
  total: 0
})
const changeItem = (value) => {
  router.push({ name: "addLocation", query: data.value[value] })
}
const deleteItem = (value) => {
  deleteLocation({ dpId: data.value[value].dpId }).then((res) => {
    getData("init")
  })
}
// 添加
const addLocation = () => {
  router.push("addLocation")
}
// 搜索
const onSearch = async (searchValue) => {
  pagination.value.current = 1
  getData("search", searchValue)

}

//分页
let changeTable = (page) => {
  pagination.value = page
  if (value.value === "") {
    getData("init")
  } else {
    getData("search", value.value)
  }
}

// 刷新数据
const getData = async (method, Value) => {
  let res = null
  if (method === "init") {
    res = await getLocation({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize
    })

  } else {
    if (searchOption.value === "投放点") {
      res = await getLocation({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize,
        dpName: Value
      })
    } else {
      res = await getLocation({
        pageNum: pagination.value.current,
        pageSize: pagination.value.pageSize,
        dpZone: Value
      })
    }

  }
  if (res.code === 200) {
    pagination.value.total = res.data.total
    data.value = res.data.list.map((item, index) => {
      item.key = index;
      item.vacancies = item.dpAll - item.dpStock
      return item
    })
  }
}
onMounted(() => {
  getData("init")
})
</script>


<style></style>
