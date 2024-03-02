import { atom } from 'recoil';

export const isLoggedInState = atom({
  key: 'isLoggedInState',
  default: false,
});

export const accessTokenState = atom({
  key: 'accessTokenState',
  default: undefined,
});

export const refreshTokenState = atom({
  key: 'refreshTokenState',
  default: undefined,
});
