import React from 'react';
import axios from '../util/axiosConfig';

const MyPage: React.FC = () => {
  const handleLogout = async () => {
    try {
      await axios.get('http://localhost:8080/api/users/signout');
      // 로그아웃 성공한 경우, 사용자 정보 등을 초기화하거나 리디렉션 등의 작업을 수행할 수 있습니다.
      // 예시로 페이지 리로드를 수행합니다.
    } catch (error) {
      console.error('로그아웃 중 에러:', error);
    }
  };

  return (
    <div>
      <h2>Welcome to My Page!</h2>
      {/* 로그아웃 버튼 */}
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default MyPage;
