import React, { useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';

const FormContainer = styled.div`
  max-width: 600px;
  margin: 200px auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-height: calc(100vh - 40px);
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

const Select = styled.select`
  width: 100%;
  padding: 0.5rem;
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

const JoinButton = styled.button`
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

const SendCodeButton = styled.button`
  background-color: #008cba;
  border: 2px solid #008cba;
  padding: 1px 2px;
  margin-left: 10px;
  color: #ffffff;
  font-weight: 600;
  font-size: 15px;
  margin-top: 20px;
  cursor: pointer;
  border-radius: 5px;
  transition:
    background-color 0.3s,
    border-color 0.3s;

  &:hover {
    background-color: #005f6b;
    border-color: #005f6b;
  }
`;
const AuthCodeInput = styled.input`
  width: 100%;
  padding: 0.5rem;
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  margin-top: -3px;
`;

const JoinPage: React.FC = () => {
  const [affiliation, setAffiliation] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [authCode, setAuthCode] = useState('');

  const handleAffiliationChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setAffiliation(event.target.value);
  };

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handlePasswordConfirmChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPasswordConfirm(event.target.value);
  };
  const handleAuthCodeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setAuthCode(event.target.value);
  };

  const sendCode = () => {
    console.log(`Authentication code sent to: ${email}`);
  };

  return (
    <FormContainer>
      <Form>
        <FormGroup>
          <Label>소속</Label>
          <Select value={affiliation} onChange={handleAffiliationChange}>
            <option value="">선택하세요</option>
            <option value="option1">Techeer</option>
            <option value="option2">Techeer Partners</option>
          </Select>
        </FormGroup>
        <FormGroup>
          <Label>이메일</Label>
          <EmailInput type="email" value={email} onChange={handleEmailChange} />
          <SendCodeButton onClick={sendCode}>인증</SendCodeButton>
        </FormGroup>
        <FormGroup>
          <Label>인증코드</Label>
          <AuthCodeInput type="text" value={authCode} onChange={handleAuthCodeChange} />
        </FormGroup>
        <FormGroup>
          <Label>비밀번호</Label>
          <PasswordInput type="password" value={password} onChange={handlePasswordChange} />
        </FormGroup>
        <FormGroup>
          <Label>비밀번호 확인</Label>
          <PasswordInput type="password" value={passwordConfirm} onChange={handlePasswordConfirmChange} />
        </FormGroup>
        <div style={{ display: 'flex', justifyContent: 'flex-end', width: '100%' }}>
          <JoinButton type="submit">가입하기</JoinButton>
        </div>
      </Form>
    </FormContainer>
  );
};

export default JoinPage;
