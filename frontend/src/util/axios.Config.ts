import axios from 'axios';

const instance = axios.create({
  withCredentials: true,
  // 다른 Axios 설정도 추가 가능
});

export default instance;
