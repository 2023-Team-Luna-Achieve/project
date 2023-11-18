import React, { useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';

const FormContainer = styled.div`
  max-width: 600px;
  margin: 300px auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-height: 100vh;
  overflow: hidden;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const FormGroup = styled.div`
  margin-bottom: 1rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
`;

const Label = styled.label`
  font-weight: bold;
  width: 150px;
  text-align: right;
  margin-left: 0px;
`;

const PasswordInput = styled.input`
  width: 100%;
  padding: 0.5rem;
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  margin-top: -3px;
`;

const EmailInput = styled.input`
  width: 100%;
  padding: 0.5rem;
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  margin-top: -3px;
`;

const LoginButton = styled.button`
  background-color: #c0c0c0;
  border: 2px solid #c0c0c0;
  padding: 10px 40px;
  margin-left: 10px;
  color: #ffffff;
  font-weight: 600;
  font-size: 16px;
  margin-top: 20px;
  cursor: pointer;
  border-radius: 5px;
  transition:
    background-color 0.3s,
    border-color 0.3s,
    color 0.3s;

  &:hover {
    background-color: #000000;
    border-color: #000000;
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
      const response = await axios.post(
        'http://localhost:8080/api/users/signin',
        {
          email,
          password,
        },
        { withCredentials: true },
      ); // withCredentials 설정 추가

      // 응답을 처리하고, 예를 들어 성공 시 새로운 페이지로 리다이렉트
      console.log('로그인 성공:', response.data);
    } catch (error) {
      // 에러 처리
      console.error('로그인 실패:', error);
    }
  };

  return (
    <FormContainer>
      <Form onSubmit={handleLogin}>
        <FormGroup>
          <Label>이메일</Label>
          <EmailInput type="email" value={email} onChange={handleEmailChange} />
        </FormGroup>
        <FormGroup>
          <Label>비밀번호</Label>
          <PasswordInput type="password" value={password} onChange={handlePasswordChange} />
        </FormGroup>
        <div style={{ display: 'flex', justifyContent: 'flex-end', width: '100%' }}>
          <LoginButton type="submit">로그인</LoginButton>
        </div>
      </Form>
    </FormContainer>
  );
};

export default LoginPage;
