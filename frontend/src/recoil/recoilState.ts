import { atom } from 'recoil';

// 로컬 스토리지에서 토큰을 가져오는 함수
const getAccessTokenFromLocalStorage = () => localStorage.getItem('accessToken');
const getRefreshTokenFromLocalStorage = () => localStorage.getItem('refreshToken');

const initialAccessToken = getAccessTokenFromLocalStorage();
const initialRefreshToken = getRefreshTokenFromLocalStorage();
const initialIsLoggedIn = Boolean(initialAccessToken && initialRefreshToken);

export const isLoggedInState = atom({
  key: 'isLoggedInState',
  default: initialIsLoggedIn,
});

export const accessTokenState = atom({
  key: 'accessTokenState',
  default: initialAccessToken,
});

export const refreshTokenState = atom({
  key: 'refreshTokenState',
  default: initialRefreshToken,
});
