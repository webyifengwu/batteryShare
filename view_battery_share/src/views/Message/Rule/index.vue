<template>
    <div>
        <h2>租借规则 - 共享充电宝系统</h2>

<div class="title">欢迎使用我们的共享充电宝系统！为了确保顺畅的租借体验和公平的服务，以下是我们的租借规则：
</div>
        <ul class="rule">
            <li class="rule_item">
                <div class="item">1. 注册与账户：</div>
                <div class="text">  在租借充电宝之前，用户需要注册一个账户并提供必要的个人信息。
                     用户应确保提供的个人信息准确、完整，并保证信息的更新及时。</div>
            </li>
            <li class="rule_item">
                <div class="item">2. 租借流程：</div>
                <div class="text">
                     用户可以通过我们的移动应用程序或指定的自助终端租借充电宝。
                     在租借过程中，用户应仔细检查充电宝的外观和功能，并确保充电宝的可用电量符合需求。
                     用户可以选择租借不同类型的充电宝，根据自己的设备和充电需求选择合适的充电宝型号。</div>

            </li>
            <li class="rule_item">
                <div class="item"> 3. 使用期限：
                </div>
                <div class="text">  每次租借的充电宝使用期限为指定的时间段，通常以小时为单位计算。
                     用户需要在租借期限内归还充电宝，超过期限将可能产生额外费用或罚款。</div>
            </li>
            <li class="rule_item">
                <div class="item"> 4. 归还充电宝：
                </div>
                <div class="text">  在使用完充电宝后，用户应准时归还充电宝。
                     归还可以通过我们的移动应用程序或指定的自助终端完成。
                     用户需要确保将充电宝正确放置在指定的归还位置或设备中。</div>

            </li>
            <li class="rule_item">
                <div class="item"> 5. 费用：
                </div>
                <div class="text">  
                     租借充电宝的费用将根据租借时长和所选充电宝类型进行计费。
                     不满15小时,总金额为充电宝租借价格*时长,超过15小时未满24小时,统一按15小时计算。</div>

            </li>
            <li class="rule_item">
                <div class="item"> 6. 使用限制：
                </div>
                <div class="text"> 用户在租借充电宝期间应妥善使用和保管充电宝，不得故意损坏、盗窃或滥用充电宝。
                     用户不得将充电宝出租、转借给他人使用。
                     用户需遵守当地法律法规和相关规定，不得将充电宝用于非法活动或违规行为。</div>

            </li>
            <li class="rule_item">
                <div class="item"> 7. 丢失或损坏：
                </div>
                <div class="text">  如果充电宝在使用过程中丢失或损坏，用户需要立即联系我们的客服团队，并按照指示采取相应措施。
                     用户可能需要承担充电宝丢失、损坏或修复所产生的费用。</div>
            </li>
        </ul>


        <div class="tip"> 请在租借充电宝之前详细阅读以上规则，并遵守我们的租借规定。如有任何疑问或需要帮助，请随时联系我们的客服团队。祝您使用愉快！
        </div>
    </div>
</template>

<script setup>

</script>


<style lang="scss">
h2{

}
.rule{
    text-align: left;
    .rule_item{
        .item{
            margin-bottom:10px;
        }
        .text{
            color: #C1C1C1;
            margin-bottom: 15px;
        }
    }
}

.title{
    text-align: left;
    margin-top: 15px;
    text-indent: 35px;

}
.tip{
    text-align: left;
    margin-top: 15px;
    text-indent: 35px;

}
</style>
