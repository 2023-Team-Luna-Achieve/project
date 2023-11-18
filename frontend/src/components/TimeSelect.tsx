import React from 'react';
import Select from 'react-select';

const generateTimeOptions = () => {
  const options = [];
  for (let hour = 0; hour < 24; hour++) {
    for (let minute = 0; minute < 60; minute += 30) {
      const formattedHour = hour.toString().padStart(2, '0');
      const formattedMinute = minute.toString().padStart(2, '0');
      const timeLabel = `${formattedHour}:${formattedMinute}`;
      options.push({ label: timeLabel, value: timeLabel });
    }
  }
  return options;
};

const options = generateTimeOptions();

interface TimeSelectProps {
  value: string;
  onChange: (selectedTime: string) => void;
  label: string;
}

const TimeSelect: React.FC<TimeSelectProps> = ({ value, onChange, label }) => {
  const handleChange = (selectedOption: any) => {
    const selectedTime = selectedOption?.value as string;
    console.log(`Selected ${label}:`, selectedTime);
    onChange(selectedTime);
  };

  return (
    <div>
      <label>{label}</label>
      <Select options={options} value={{ label: value, value }} onChange={handleChange} />
    </div>
  );
};

export default TimeSelect;
