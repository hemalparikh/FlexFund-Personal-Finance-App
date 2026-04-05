<div align="center">

# 💸 FlexFund
### *Personal Finance Companion App*

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/UI-XML-FF6F00?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Storage-SharedPreferences-1E88E5?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Status-Submission%20Ready-success?style=for-the-badge" />
</p>

<p align="center">
  A modern Android personal finance companion app designed to help users track transactions, manage savings goals, and understand spending behavior through a clean, engaging, and mobile-first experience.
</p>

</div>

---

## ✨ Overview

**FlexFund** is a polished Android finance app built with **Kotlin + XML**, focused on helping users become more aware of their money habits through:

- real-time financial tracking
- intuitive mobile UX
- personalized settings
- actionable spending insights

Rather than being just a static finance dashboard, FlexFund is designed to feel like a **thoughtful personal finance product**.

---

## 🚀 Core Features

### 🏠 Home Dashboard
- Current balance overview
- Total income summary
- Total expense summary
- Savings progress / financial overview
- Real-time dashboard updates

### 💸 Transaction Tracking
- Add new transactions
- View transaction history
- Edit transactions
- Delete transactions
- Search transactions
- Filter by:
  - All
  - Income
  - Expense

### 🎯 Goals & Challenges
- Monthly savings goal tracker
- Dynamic progress bar
- Smart motivation section
- Mini challenge cards

### 📊 Insights Screen
- Top spending category
- Spending behavior insight
- Smart money suggestions
- Weekly-style pattern analysis

### ⚙️ Profile & Settings
- Save user name
- Set monthly savings goal
- Choose preferred currency
- Toggle reminder preference

### 🌍 Multi-Currency Support
Supports:
- INR (₹)
- USD ($)
- EUR (€)
- GBP (£)
- JPY (¥)

### 📁 Data Export
- Export transactions as a shareable file
- Share via Android share sheet

### 🎬 UI / UX Enhancements
- Animated transitions
- Empty state handling
- Smooth mobile navigation
- Fintech-inspired modern interface

---

## 🧠 Real-Time Dynamic App Behavior

FlexFund is not built as a static UI demo.

The app dynamically updates values across screens whenever transactions are:
- added
- edited
- deleted

### Dynamically updated values include:
- Current balance
- Total income
- Total expenses
- Savings progress
- Goals screen data
- Insights screen calculations
- Currency symbol display

---

## 📱 Screens Included

- **Home Dashboard**
- **Transactions**
- **Add / Edit Transaction**
- **Goals**
- **Insights**
- **Profile & Settings**

---

## 🛠️ Tech Stack

| Category | Technology |
|---------|------------|
| Language | Kotlin |
| UI | XML |
| Architecture | Fragment-based Android app |
| Storage | SharedPreferences |
| Components | RecyclerView, Bottom Navigation, Material Components, FileProvider |

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
