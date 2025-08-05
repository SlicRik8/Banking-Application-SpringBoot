import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/Dashboard.css';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
  const [account, setAccount] = useState(null);
  const [amount, setAmount] = useState('');
  const [recipient, setRecipient] = useState('');
  const [action, setAction] = useState('deposit');
  const navigate = useNavigate();

  const username = localStorage.getItem('username');
  const token = localStorage.getItem('token');

  useEffect(() => {
    if (!token || !username) {
      navigate('/login');
      return;
    }

    const fetchAccount = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/accounts/user/${username}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setAccount(response.data);
      } catch (error) {
        console.error('Error fetching account:', error);
        alert('Failed to fetch account info. Please log in again.');
        navigate('/login');
      }
    };

    fetchAccount();
  }, [token, username, navigate]);
   const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

 const handleTransaction = async () => {
  if (!amount || isNaN(amount) || parseFloat(amount) <= 0) {
    alert('Please enter a valid amount');
    return;
  }

  try {
    let response;
    const payload = {
      accountId: account.id,
      amount: parseFloat(amount),
    };

    if (action === 'deposit') {
      response = await axios.put(`http://localhost:8080/api/accounts/deposit`, payload, {
        headers: { Authorization: `Bearer ${token}` },
      });
    } else if (action === 'withdraw') {
      response = await axios.put(`http://localhost:8080/api/accounts/withdraw`, payload, {
        headers: { Authorization: `Bearer ${token}` },
      });
    } else if (action === 'transfer') {
      if (!recipient || isNaN(recipient)) {
        alert('Please enter a valid recipient account ID');
        return;
      }

      const transferPayload = {
        toAccountId: parseInt(recipient, 10),
        amount: parseFloat(amount),
      };

      response = await axios.post(`http://localhost:8080/api/accounts/transfer`, transferPayload, {
        headers: { Authorization: `Bearer ${token}` },
      });
    }

    setAccount(response.data);
    setAmount('');
    setRecipient('');
    alert(`${action.charAt(0).toUpperCase() + action.slice(1)} successful!`);
  } catch (error) {
    console.error(`${action} error:`, error);
    alert(`Failed to ${action}: ${error.response?.data?.message || 'Unknown error'}`);
  }
};

  if (!account) {
    return <div className="dashboard-container">Loading account info...</div>;
  }

  return (
    <div className="dashboard-wrapper">
      <div className="dashboard-header">
        <h2 className="dashboard-title" >Dashboard</h2>
      <button className="logout-button" onClick={handleLogout}>Logout</button>
        
      
      </div>

      <div className="dashboard-container">
        <div className="account-info">
          <h2>Account Info</h2>
          <p><strong>Account Holder:</strong> {account.accountHolderName}</p>
          <p><strong>Balance:</strong> ₹{account.balance.toFixed(2)}</p>
        </div>


      <div className="transaction-form">
        <h2>Transaction</h2>
        <select value={action} onChange={(e) => setAction(e.target.value)}>
          <option value="deposit">Deposit</option>
          <option value="withdraw">Withdraw</option>
          <option value="transfer">Transfer</option>
        </select>

        {action === 'transfer' && (
          <input
            type="number"
            placeholder="Recipient Account ID"
            value={recipient}
            onChange={(e) => setRecipient(e.target.value)}
          />
        )}

        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        <button onClick={handleTransaction}>
          {action.charAt(0).toUpperCase() + action.slice(1)}
        </button>
      </div>

      <div className="transaction-history">
        <h2>Transaction History</h2>
        {account.transactions.length === 0 ? (
          <p>No transactions found.</p>
        ) : (
          <ul>
            {account.transactions.map((txn, index) => (
              <li key={index}>
                <strong>{txn.type}</strong> ₹{txn.amount} on {txn.timestamp}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
    </div>
  );
}

export default Dashboard;
