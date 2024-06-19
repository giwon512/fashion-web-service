import React, { useState } from 'react';
import style from './survey.css';
import { useNavigate } from 'react-router-dom';

const Survey = () => {
  const [formData, setFormData] = useState({
    gender: '',
    ageGroup: [],
    styles: [],
    brands: []
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, checked, type } = e.target;
    if (type === 'checkbox') {
      setFormData(prevState => {
        const isChecked = checked;
        const prevValues = prevState[name];
        let newValues;
        if (isChecked) {
          newValues = [...prevValues, value];
        } else {
          newValues = prevValues.filter(v => v !== value);
        }
        return { ...prevState, [name]: newValues };
      });
    } else {
      setFormData(prevState => ({ ...prevState, [name]: value }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitted Data: ', formData);
    navigate('/');
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <h3>성별</h3>
        <label>
          <input
            type="radio"
            name="gender"
            value="male"
            checked={formData.gender === 'male'}
            onChange={handleChange}
          />
          남성
        </label>
        <label>
          <input
            type="radio"
            name="gender"
            value="female"
            checked={formData.gender === 'female'}
            onChange={handleChange}
          />
          여성
        </label>
      </div>
      
      <div>
        <h3>연령</h3>
        {['10대', '20대', '30대', '40대'].map(age => (
          <label key={age}>
            <input
              type="checkbox"
              name="ageGroup"
              value={age}
              checked={formData.ageGroup.includes(age)}
              onChange={handleChange}
            />
            {age}
          </label>
        ))}
      </div>
      
      <div>
        <h3>스타일</h3>
        {['미니멀', '클래식', '스트릿', '아메카지', '캐주얼', '빈티지'].map(style => (
          <label key={style}>
            <input
              type="checkbox"
              name="styles"
              value={style}
              checked={formData.styles.includes(style)}
              onChange={handleChange}
            />
            {style}
          </label>
        ))}
      </div>
      
      <div>
        <h3>브랜드</h3>
        {['구찌', '나이키', '자라', '폴로', '스투시'].map(brand => (
          <label key={brand}>
            <input
              type="checkbox"
              name="brands"
              value={brand}
              checked={formData.brands.includes(brand)}
              onChange={handleChange}
            />
            {brand}
          </label>
        ))}
      </div>
      
      <button type="submit">제출</button>
    </form>
  );
};

export default Survey;
