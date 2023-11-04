import React, { useState, useEffect } from 'react';

interface TimeSelectProps {
  label: string; // 시작 시간 또는 종료 시간을 나타내는 라벨
}

const TimeSelect: React.FC<TimeSelectProps> = ({ label }) => {
  const [selectedTime, setSelectedTime] = useState('');
  const [timeSlots, setTimeSlots] = useState<string[]>([]);
  const [currentPage, setCurrentPage] = useState(2);
  const slotsPerPage = 10;

  useEffect(() => {
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

  const getCurrentPageTimeSlots = () => {
    const startIndex = currentPage * slotsPerPage;
    const endIndex = startIndex + slotsPerPage;
    return timeSlots.slice(startIndex, endIndex);
  };

  const goToPreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
    }
  };

  const goToNextPage = () => {
    const totalPages = Math.ceil(timeSlots.length / slotsPerPage);
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1);
    }
  };

  return (
    <div>
      <label htmlFor="time-select">{label}</label>
      <select id="time-select" value={selectedTime} onChange={handleTimeChange} aria-label={label}>
        <option value="">Select a time</option>
        {getCurrentPageTimeSlots().map((time) => (
          <option key={time} value={time}>
            {time}
          </option>
        ))}
      </select>
      <div>
        <button onClick={goToPreviousPage}>이전</button>
        <button onClick={goToNextPage}>다음</button>
      </div>
    </div>
  );
};

export default TimeSelect;
