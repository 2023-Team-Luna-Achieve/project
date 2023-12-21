import axios from 'axios';

const instance = axios.create({
  withCredentials: true, // 여기에 withCredentials 설정을 추가
  // 다른 Axios 설정도 추가 가능
});

export default instance;
