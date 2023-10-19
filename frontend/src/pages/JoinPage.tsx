import React, { useState } from 'react';
import styled from 'styled-components';

const FormContainer = styled.div`
  max-width: 400px;
  margin: 0 auto;
`;

const FormGroup = styled.div`
  margin-bottom: 1rem;
`;

const Label = styled.label`
  display: block;
  font-weight: bold;
`;

const Select = styled.select`
  width: 100%;
  padding: 0.5rem;
`;

const Option = styled.option``;

const PasswordInput = styled.input`
  width: 100%;
  padding: 0.5rem;
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
      <form>
        <FormGroup>
          <Label>소속</Label>
          <Select value={affiliation} onChange={handleAffiliationChange}>
            <Option value="">선택하세요</Option>
            <Option value="option1">Techeer</Option>
            <Option value="option2">Techeer Partners</Option>
          </Select>
        </FormGroup>
        <FormGroup>
          <Label>이메일</Label>
          <input type="email" value={email} onChange={handleEmailChange} />
        </FormGroup>
        <FormGroup>
          <Label>비밀번호</Label>
          <PasswordInput type="password" value={password} onChange={handlePasswordChange} />
        </FormGroup>
        <FormGroup>
          <Label>비밀번호 확인</Label>
          <PasswordInput type="password" value={passwordConfirm} onChange={handlePasswordConfirmChange} />
        </FormGroup>
        <button type="submit">가입하기</button>
      </form>
    </FormContainer>
  );
};

export default JoinPage;
