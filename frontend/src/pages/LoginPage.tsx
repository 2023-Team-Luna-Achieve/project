import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import axios from '../util/axiosConfig';
import Modal from '../components/Modal';
import { useSetRecoilState } from 'recoil'; // Recoil 추가
import { accessTokenState, refreshTokenState, isLoggedInState } from '../recoil/recoilState';

const FormContainer = styled.div`
  max-width: 600px;
  margin: 50px auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-height: calc(100vh - 40px);
  overflow: hidden;
  color: #585858;
`;
const LoginText = styled.div`
  font-size: 70px;
  font-weight: bold;
  color: #3a3a3a;
  text-align: center;
  margin-top: 60px;
  margin-bottom: 0px;
`;
const Input = styled.input`
  border-radius: 0;
  border: 0.7px solid #c0c0c0;
  height: 26px;
  width: 400px;
`;
const PasswordInput = styled(Input).attrs({ type: 'password', autoComplete: 'new-password' })`
  margin-bottom: 0px;
  border-radius: 0;
`;
const StyledForm = styled.form``;
const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
`;
const LoginButton = styled.button`
  border: none;
  background-color: #c0c0c0;
  color: #ffffff;
  font-size: 16px;
  height: 26px;
  width: 100%;
  transition:
    background-color 0.3s,
    color 0.3s;
  &:hover {
    background-color: #000000;
  }
`;
const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const setAccessToken = useSetRecoilState(accessTokenState);
  const setRefreshToken = useSetRecoilState(refreshTokenState);
  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('/api/user/sign-in', { email, password });
      const accessTokenFromHeader = response.headers['authorization'];
      const refreshTokenFromHeader = response.headers['refresh-token'];

      if (accessTokenFromHeader && accessTokenFromHeader.startsWith('Bearer ')) {
        const accessToken = accessTokenFromHeader.split(' ')[1]; // 'Bearer ' 제거
        setAccessToken(accessToken);
        localStorage.setItem('accessToken', accessToken);
      }

      if (refreshTokenFromHeader) {
        setRefreshToken(refreshTokenFromHeader);
        localStorage.setItem('refreshToken', refreshTokenFromHeader);
        setIsLoggedIn(true); // 로그인 상태를 true로 설정
      }

      setIsModalOpen(true); // 모달 열기
    } catch (error) {
      console.error('로그인 실패:', error);
    }
  };

  const closeModalAndRedirect = () => {
    setIsModalOpen(false);
    navigate('/main'); // 로그인 성공 후 리디렉션
  };

  return (
    <FormContainer>
      <Modal isOpen={isModalOpen} onClose={closeModalAndRedirect}>
        <p>로그인이 완료 되었습니다.</p>
      </Modal>
      <LoginText className="login">로그인</LoginText>
      <StyledForm onSubmit={handleLogin}>
        <FormGroup>
          <label htmlFor="name">이메일</label>
          <Input type="email" id="email" name="email" value={email} onChange={handleEmailChange} />
        </FormGroup>
        <FormGroup>
          <label htmlFor="password">비밀번호</label>
          <PasswordInput id="password" name="password" value={password} onChange={handlePasswordChange} />
        </FormGroup>
        <div>
          <LoginButton type="submit">로그인</LoginButton>
        </div>
      </StyledForm>
    </FormContainer>
  );
};

export default LoginPage;
