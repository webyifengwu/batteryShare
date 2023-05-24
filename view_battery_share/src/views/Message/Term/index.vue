<template>
  <div class="term">
    
    <div class="chat">
      <input type="text" class="input">
      <button class="btn">发送</button>
    </div>

  </div>
</template>

<script setup>

</script>

<style scoped lang="scss">
.term{
.chat {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 50px;
  background-color: #F5F5F5;

  .input {
    position: absolute;
    left: 10px;
    top: 5px;
    width: 75%;
    height: 70%;
  }

  .btn {
    position: absolute;
    top: 5px;
    right: 0;
    height: 75%;
    width: 20%;
  }

}
}
</style>
