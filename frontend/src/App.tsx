import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainPage from './pages/MainPage';
import Header from './components/layout/Header';
import ReservationPage from './pages/ReservationPage';
import NoticePage from './pages/NoticePage';
import LoginPage from './pages/LoginPage';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <Header />
        <Routes>
          <Route path="Main" element={<MainPage />} />
          <Route path="Reservation" element={<ReservationPage />} />
          <Route path="Notice" element={<NoticePage />} />
          <Route path="Login" element={<LoginPage />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
