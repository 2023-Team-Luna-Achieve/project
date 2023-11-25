import React, { useState } from 'react';
import styled from 'styled-components';
import axios from '../util/axiosConfig';
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
  color: #3A3A3A;
  text-align: center;
  margin-top: 60px;
  margin-bottom: 0px;
`;
const Input = styled.input`
  border-radius: 0;
  border: 0.7px solid #C0C0C0;
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
  background-color: #C0C0C0;
  color: #FFFFFF;
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
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };
  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };
  const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      // signin 엔드포인트로 API 호출
      const response = await axios.post('http://localhost:8080/api/users/signin', {
        email,
        password,
      }); // withCredentials 설정 추가
      // 응답을 처리하고, 예를 들어 성공 시 새로운 페이지로 리다이렉트 + 전역적으로 로그인 상태 확인중인지 받기
      console.log('로그인 성공:', response.data);
      await handleLoginVerification();
    } catch (error) {
      // 에러 처리
      console.error('로그인 실패:', error);
    }
  };
  const handleLoginVerification = async () => {
    try {
      // 로그인 검증을 위한 요청
      const confirmResponse = await axios.get('http://localhost:8080/api/users/login-confirm');
      // 로그인 검증 응답을 확인하고 필요에 따라 처리
      console.log('로그인 검증 성공:', confirmResponse.data);
      // 여기서 로그인 검증이 성공했으므로 적절한 처리를 수행하면 됩니다.
    } catch (error) {
      // 에러 처리
      console.error('로그인 검증 실패:', error);
    }
  };
  return (
    <FormContainer>
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
