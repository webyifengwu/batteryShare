
import { setSession } from "@/API/session"
const mutations = {
  setUsername(state, value) {
    state.username = value
    setSession("username", value)
  },
  setBalance(state, value) {
    state.blance = value
    setSession("balance", value)

  },
  setAvatar(state, value) {
    state.avatar = value
    setSession("avatar", value)

  },
  setPhone(state, value) {
    state.phone = value
    setSession("phone", value)

  },
  setUserId(state, value) {
    state.userId = value
    setSession("userId", value)
  },
  setZone(state, value) {
    state.zone = value
    setSession("zone", value)
  },
  deleteAllState(state) {

    state = {}
    localStorage.clear()
    // console.log(state);
  }

}
export default mutations