# FlexFund – Personal Finance Companion App

FlexFund is a modern Android personal finance companion app designed to help users track transactions, monitor savings goals, and understand spending behavior through a clean, engaging, and mobile-friendly experience.

The app is built with **Kotlin + XML** and focuses on combining **practical financial tracking** with **thoughtful product design**, rather than just displaying static dashboards.

---

## ✨ Features

### 🏠 Home Dashboard
- View **current balance**
- Track **total income**
- Track **total expenses**
- Quick financial summary cards
- Real-time dashboard updates based on transactions
- Progress-based financial overview

### 💸 Transaction Tracking
- Add new transactions
- View transaction history
- Edit existing transactions
- Delete transactions
- Search transactions
- Filter by:
  - All
  - Income
  - Expense
- Persistent local transaction storage

### 🎯 Goals & Challenges
- Monthly savings goal tracker
- Dynamic savings progress bar
- Smart motivation section
- Mini challenge cards for better engagement

### 📊 Insights Screen
- Top spending category
- Spending pattern analysis
- Smart financial suggestions
- Weekly-style expense behavior insight

### ⚙️ Profile & Settings
- Save user name
- Save monthly savings goal
- Choose preferred currency
- Notification preference toggle

### 🌍 Multi-Currency Support
- Supports:
  - INR (₹)
  - USD ($)
  - EUR (€)
  - GBP (£)
  - JPY (¥)
- Currency preference is reflected across the app

### 📁 Data Export
- Export transactions as a shareable text file
- Share/export through Android share sheet

### 🎬 UI / UX Enhancements
- Animated screen transitions
- Smooth form interactions
- Modern fintech-inspired UI
- Responsive layout for Android devices
- Empty state for transactions screen

---

## 🧠 Real-Time Dynamic Behavior

FlexFund is not just a static UI demo.

The app dynamically updates across screens when transactions are added, edited, or deleted.

### Real-time updates include:
- Current balance
- Total income
- Total expenses
- Savings goal progress
- Goal screen values
- Insights screen calculations
- Currency display updates

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** XML
- **Architecture:** Fragment-based Android app
- **Storage:** SharedPreferences
- **Components Used:**
  - RecyclerView
  - Material Components
  - Bottom Navigation
  - Fragment Navigation
  - DatePickerDialog
  - FileProvider

---

## 📂 Project Structure

```bash
com.example.flexfund
 ┣ model
 ┃ ┗ Transaction.kt
 ┣ utils
 ┃ ┣ TransactionStorage.kt
 ┃ ┣ AppSettings.kt
 ┃ ┗ FinanceCalculator.kt
 ┣ ui
 ┃ ┣ home
 ┃ ┃ ┗ HomeFragment.kt
 ┃ ┣ transactions
 ┃ ┃ ┣ TransactionsFragment.kt
 ┃ ┃ ┣ TransactionAdapter.kt
 ┃ ┃ ┗ AddTransactionActivity.kt
 ┃ ┣ goals
 ┃ ┃ ┗ GoalsFragment.kt
 ┃ ┣ insights
 ┃ ┃ ┗ InsightsFragment.kt
 ┃ ┗ profile
 ┃   ┗ ProfileFragment.kt
 ┗ MainActivity.kt

 
