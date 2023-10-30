import React, { useState } from 'react';
import styled from 'styled-components';

const FormContainer = styled.div`
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
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
  text-align: right; /* Right-align the label text */
  margin-left: 0px; /* Add left margin as needed */
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

const JoinPage: React.FC = () => {
  const [affiliation, setAffiliation] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');

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
          <button type="submit">가입하기</button>
        </div>
      </Form>
    </FormContainer>
  );
};

export default JoinPage;
