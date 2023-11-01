import React, { useState, useEffect } from 'react';

const TimeSelect: React.FC = () => {
  const [selectedTime, setSelectedTime] = useState('');
  const [timeSlots, setTimeSlots] = useState<string[]>([]); // 전체 시간 슬롯
  const [currentPage, setCurrentPage] = useState(2); // 3번째 페이지부터 시작
  const slotsPerPage = 10; // 한 페이지에 표시할 슬롯 수

  useEffect(() => {
    // 전체 시간 슬롯 생성 (30분 간격)
    const allTimeSlots = [];
    for (let hour = 0; hour < 24; hour++) {
      for (let minute = 0; minute < 60; minute += 30) {
        const time = `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
        allTimeSlots.push(time);
      }
    }
    setTimeSlots(allTimeSlots);
  }, []);

  const handleTimeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedTime(e.target.value);
  };

  // 현재 페이지의 시간 슬롯 가져오기
  const getCurrentPageTimeSlots = () => {
    const startIndex = currentPage * slotsPerPage; // 현재 페이지부터 시작
    const endIndex = startIndex + slotsPerPage;
    return timeSlots.slice(startIndex, endIndex);
  };

  // 이전 페이지로 이동
  const goToPreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
    }
  };

  // 다음 페이지로 이동
  const goToNextPage = () => {
    const totalPages = Math.ceil(timeSlots.length / slotsPerPage);
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1);
    }
  };

  return (
    <div>
      <select value={selectedTime} onChange={handleTimeChange}>
        <option value="">Select a time</option>
        {getCurrentPageTimeSlots().map((time) => (
          <option key={time} value={time}>
            {time}
          </option>
        ))}
      </select>

      <div>
        {/* 이전 페이지 및 다음 페이지 이동 버튼 */}
        <button onClick={goToPreviousPage}>Previous</button>
        <button onClick={goToNextPage}>Next</button>
      </div>
    </div>
  );
};

export default TimeSelect;
