import React, { useState } from 'react';
import styled from 'styled-components';
import { Formik, FormikHelpers } from 'formik';
import * as Yup from 'yup';
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

const StyledForm = styled.form``;

const FormGroup = styled.div``;

const Select = styled.select``;

const Input = styled.input``;

const PasswordInput = styled(Input).attrs({ type: 'password' })``;

const JoinButton = styled.button`
  background-color: #c0c0c0;
  color: #ffffff;
  font-size: 16px;
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

const SendCodeButton = styled(JoinButton)`
  background-color: #008cba;
  border: 2px solid #008cba;
  font-size: 15px;

  &:hover {
    background-color: #005f6b;
    border-color: #005f6b;
  }
`;

const ConfirmAuthButton = styled(SendCodeButton)``;

const AuthCodeInput = styled(Input)``;

const EmailInput = styled(Input).attrs({ type: 'email' })``;

const validationSchema = Yup.object().shape({
  affiliation: Yup.string().required('소속을 선택하세요'),
  name: Yup.string().required('이름을 입력하세요'),
  email: Yup.string().email('올바른 이메일 형식이 아닙니다').required('이메일을 입력하세요'),
  authCode: Yup.string().required('인증코드를 입력하세요'),
  password: Yup.string().required('비밀번호를 입력하세요'),
  passwordConfirm: Yup.string()
    .oneOf([Yup.ref('password')], '비밀번호가 일치하지 않습니다')
    .required('비밀번호 확인을 입력하세요'),
});

const initialValues = {
  affiliation: '',
  name: '',
  email: '',
  authCode: '',
  password: '',
  passwordConfirm: '',
};

type YourFormValuesType = {
  affiliation: string;
  name: string;
  email: string;
  authCode: string;
  password: string;
  passwordConfirm: string;
};

const JoinPage: React.FC = () => {
  const [affiliation, setAffiliation] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [authCode, setAuthCode] = useState('');
  const [name, setName] = useState('');

  const handleSubmit = async (values: YourFormValuesType, { setSubmitting }: FormikHelpers<YourFormValuesType>) => {
    try {
      await axios.post('http://localhost:8080/api/email/verification/request', {
        email: values.email,
      });

      console.log(`인증 이메일이 전송되었습니다: ${values.email}`);

      await axios.post('http://localhost:8080/api/email/verification/confirm', {
        email: values.email,
        code: values.authCode,
      });

      console.log(`이메일 인증이 성공적으로 확인되었습니다: ${values.email}`);
    } catch (error) {
      console.error('데이터 제출 중 에러:', error);
    } finally {
      setSubmitting(false);
    }
  };

  const handleInputChange = (setter: React.Dispatch<React.SetStateAction<string>>) => {
    return (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
      setter(event.target.value);
    };
  };

  const sendCode = () => {
    console.log(`메일을 보냈습니다: ${email}`);
  };

  return (
    <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}>
      {({ isSubmitting }) => (
        <FormContainer>
          <StyledForm>
            <FormGroup>
              <label htmlFor="affiliation">소속</label>
              <Select
                id="affiliation"
                name="affiliation"
                value={affiliation}
                onChange={handleInputChange(setAffiliation)}
              >
                <option value="">선택하세요</option>
                <option value="option1">Techeer</option>
                <option value="option2">Techeer Partners</option>
              </Select>
            </FormGroup>
            <FormGroup>
              <label htmlFor="name">이름</label>
              <Input type="text" id="name" name="name" value={name} onChange={handleInputChange(setName)} />
            </FormGroup>
            <FormGroup>
              <label htmlFor="email">이메일</label>
              <EmailInput id="email" name="email" value={email} onChange={handleInputChange(setEmail)} />
              <SendCodeButton onClick={sendCode} disabled={isSubmitting}>
                {isSubmitting ? '전송 중...' : '인증'}
              </SendCodeButton>
            </FormGroup>
            <FormGroup>
              <label htmlFor="authCode">인증코드</label>
              <AuthCodeInput id="authCode" name="authCode" value={authCode} onChange={handleInputChange(setAuthCode)} />
              <ConfirmAuthButton disabled={isSubmitting}>{isSubmitting ? '확인 중...' : '인증확인'}</ConfirmAuthButton>
            </FormGroup>
            <FormGroup>
              <label htmlFor="password">비밀번호</label>
              <PasswordInput id="password" name="password" value={password} onChange={handleInputChange(setPassword)} />
            </FormGroup>
            <FormGroup>
              <label htmlFor="passwordConfirm">비밀번호 확인</label>
              <PasswordInput
                id="passwordConfirm"
                name="passwordConfirm"
                value={passwordConfirm}
                onChange={handleInputChange(setPasswordConfirm)}
              />
            </FormGroup>
            <div style={{ display: 'flex', justifyContent: 'flex-end', width: '100%' }}>
              <JoinButton type="submit" disabled={isSubmitting}>
                {isSubmitting ? '가입 중...' : '가입하기'}
              </JoinButton>
            </div>
          </StyledForm>
        </FormContainer>
      )}
    </Formik>
  );
};
export default JoinPage;
