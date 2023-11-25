import React from 'react';
import axios from '../util/axiosConfig';

const MyPage: React.FC = () => {
  const handleLogout = async () => {
    try {
      await axios.post('http://localhost:8080/api/users/signout');
      console.log('로그아웃 성공');
    } catch (error) {
      console.error('로그아웃 실패', error);
    }
  };
  return (
    <div>
      <button onClick={handleLogout}>로그아웃</button>
    </div>
  );
};
export default MyPage;
