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
  margin-left: 10px; /* Add left margin as needed */
`;

const PasswordInput = styled.input`
  width: 100%;
  padding: 0.5rem;
  border: none;
  border-bottom: 1px solid black; /* Add a black underline */
  outline: none;
`;

const EmailInput = styled.input`
  width: 100%;
  padding: 0.5rem;
  border: none;
  border-bottom: 1px solid black; /* Add a black underline */
  outline: none;
`;

const JoinPage: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <FormContainer>
      <Form>
        <FormGroup>
          <Label>이메일</Label>
          <EmailInput type="email" value={email} onChange={handleEmailChange} />
        </FormGroup>
        <FormGroup>
          <Label>비밀번호</Label>
          <PasswordInput type="password" value={password} onChange={handlePasswordChange} />
        </FormGroup>
        <div style={{ display: 'flex', justifyContent: 'flex-end', width: '100%' }}>
          <button type="submit">Login</button>
        </div>
      </Form>
    </FormContainer>
  );
};

export default JoinPage;
