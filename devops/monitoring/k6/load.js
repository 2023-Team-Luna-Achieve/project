import { URL } from 'https://jslib.k6.io/url/1.0.0/index.js';
import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';

export let options = {
    stages: [
        { duration: '1m', target: 3 },
        { duration: '1m', target: 5 },
        { duration: '1m', target: 6 },
        { duration: '1m', target: 6 },
        { duration: '1m', target: 0 },
    ],

    thresholds: {
        http_req_duration: ['p(95)<138'],
    },
};

const BASE_URL = 'http://backend:8080';
const STATION_COUNT = 16;

let jwtToken = ''; // JWT를 저장할 변수

function logIn() {
    let loginResponse = http.post(`${BASE_URL}/api/user/sign-in`,
        JSON.stringify({
            "email": "jaeyoon321@naver.com",
            "password": "1105"
        }),
        {
            headers: {
                'Content-Type': 'application/json', // Content-Type을 application/json으로 설정
            },
        }
    );

    check(loginResponse, {
        'login successful': (res) => res.status === 200,
    });

    jwtToken = loginResponse.headers['Authorization']; // 응답 헤더에서 JWT 추출
}
function getPath() {
    let headers = {
        'Authorization': jwtToken, // JWT를 헤더에 포함
    };

    let pathRes = http.get(`${BASE_URL}/api/boards?category=NOTICE&cursor=0`, { headers });

    check(pathRes, {
        'success to get path': (res) => res.status === 200,
    });
}

export default function () {
    logIn(); // 로그인을 먼저 수행
    getPath();
    // getPathResults();
};
