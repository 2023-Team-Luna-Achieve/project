import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import axios from '../util/axiosConfig';
import Modal from '../components/Modal';
import { useSetRecoilState } from 'recoil'; // Recoil 추가
import { accessTokenState, refreshTokenState } from '../recoil/recoilState';

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

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const response = await axios.post('/api/user/sign-in', {
        email,
        password,
      });

      // 서버에서 'Authorization', 'Refresh-Token' 헤더를 확인
      const accessTokenFromHeader = response.headers['authorization'];
      const refreshTokenFromHeader = response.headers['refresh-token'];

      console.log('응답 헤더:', response.headers); // 새로 추가된 부분
      console.log('accessTokenFromHeader:', accessTokenFromHeader);
      console.log('refreshTokenFromHeader:', refreshTokenFromHeader);

      // 액세스 토큰과 리프레시 토큰을 상태나 로컬 스토리지에 저장
      if (accessTokenFromHeader) {
        // 'Bearer ' 부분을 제거하고 액세스 토큰만 저장
        const accessToken = accessTokenFromHeader.replace('Bearer ', '');
        setAccessToken(accessToken);
        localStorage.setItem('accessToken', accessToken);
      }

      if (refreshTokenFromHeader) {
        setRefreshToken(refreshTokenFromHeader);
        localStorage.setItem('refreshToken', refreshTokenFromHeader);
      }
      // setIsModalOpen(true);
    } catch (error) {
      console.error('로그인 실패:', error);
    }
  };

  const closeModalAndRedirect = () => {
    setIsModalOpen(false);
    navigate('/main');
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
