import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const RegistrationForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    phone: '',
    truckType: 'Semi-Truck',
    document: null
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const truckTypes = [
    'Semi-Truck',
    'Box Truck',
    'Flatbed',
    'Dump Truck',
    'Tanker',
    'Refrigerated'
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleFileChange = (e) => {
    setFormData({
      ...formData,
      document: e.target.files[0]
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    // Validation
    if (!formData.fullName || !formData.email || !formData.phone || !formData.document) {
      setError('All fields are required');
      setLoading(false);
      return;
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      setError('Please enter a valid email address');
      setLoading(false);
      return;
    }

    // Phone validation - basic check for numeric value
    const phoneRegex = /^\d{10,15}$/;
    if (!phoneRegex.test(formData.phone.replace(/[^0-9]/g, ''))) {
      setError('Please enter a valid phone number');
      setLoading(false);
      return;
    }

    try {
      // Create form data for file upload
      const data = new FormData();
      data.append('fullName', formData.fullName);
      data.append('email', formData.email);
      data.append('phone', formData.phone);
      data.append('truckType', formData.truckType);
      data.append('document', formData.document);

      // API call to register driver
      const response = await axios.post('http://localhost:8080/api/drivers/register', data);
      
      // Navigate to dashboard on success
      navigate(`/dashboard/${response.data.id}`);
    } catch (err) {
      console.error('Registration error:', err);
      setError('Failed to register. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="registration-container">
      <h2>Driver Registration</h2>
      
      {error && <div className="error-message">{error}</div>}
      
      <form onSubmit={handleSubmit} className="registration-form">
        <div className="form-group">
          <label htmlFor="fullName">Full Name</label>
          <input
            type="text"
            id="fullName"
            name="fullName"
            value={formData.fullName}
            onChange={handleChange}
            placeholder="Enter your full name"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Enter your email"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="phone">Phone Number</label>
          <input
            type="tel"
            id="phone"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            placeholder="Enter your phone number"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="truckType">Truck Type</label>
          <select
            id="truckType"
            name="truckType"
            value={formData.truckType}
            onChange={handleChange}
            required
          >
            {truckTypes.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="document">Upload ID or Truck Document</label>
          <input
            type="file"
            id="document"
            name="document"
            onChange={handleFileChange}
            accept=".pdf,.jpg,.jpeg,.png"
            required
          />
          <small>Upload a PDF or image (max 10MB)</small>
        </div>

        <button 
          type="submit" 
          className="submit-button" 
          disabled={loading}
        >
          {loading ? 'Registering...' : 'Register'}
        </button>
      </form>
    </div>
  );
};

export default RegistrationForm;