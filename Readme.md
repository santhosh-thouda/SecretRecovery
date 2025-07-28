# 🔐 Shamir Secret Sharing with Scientific Calculator

This project implements the **Shamir Secret Sharing Scheme** in Java, enhanced with **scientific calculator support** for interpreting number bases. It allows you to:
- Distribute a secret among multiple parties (shares),
- Reconstruct the secret using a threshold number of shares,
- Accept input values in various number bases (binary, decimal, octal, hexadecimal, etc.).

---

## 📌 Features

- 🔢 **Scientific base calculator** support: Automatically converts values in different bases (2 to 36) to decimal before processing.
- 🔐 Implements **Shamir's Secret Sharing**: Reconstruct the original secret using any _k_ out of _n_ shares.
- 📥 Reads shares and configuration from a structured `input.json` file.
- 📤 Outputs the reconstructed secret in the console.

---

## 📁 Project Structure

ShamirSecretSharing/
├── input.json # Input shares with base and value
├── Main.java # Main entry point
├── SecretReconstructor.java # Core logic for Shamir's scheme
├── BaseConverter.java # Handles base conversion
├── json-20230227.jar # JSON dependency (org.json)

---

## 📄 input.json Format

```json
{
  "keys": {
    "n": 4,
    "k": 3
  },
  "1": { "base": "10", "value": "4" },
  "2": { "base": "2",  "value": "111" },
  "3": { "base": "10", "value": "12" },
  "6": { "base": "4",  "value": "213" }
}
```

n: Total number of shares distributed.

k: Minimum number of shares required to reconstruct the secret.

Each numbered entry represents a share:

base: Number base (e.g., 2 = binary, 10 = decimal, 16 = hexadecimal).

value: Value of the share in that base.

✅ How to Run
Compile:
```bash
javac -cp .;json-20230227.jar Main.java
Run:
```
```bash
java -cp .;json-20230227.jar Main
⚠️ Ensure json-20230227.jar is in the same directory.
```
🧪 Sample Output
```css
Reading shares from input.json...
Reconstructed Secret: 7
```

📚 Concepts Used
Shamir's Secret Sharing (Lagrange Interpolation over Finite Fields)

Modular Arithmetic

Base Conversion Logic

JSON File Parsing (org.json)

👨‍💻 Author
Santhosh Thouda

GitHub: @santhosh-thouda

📜 License
This project is licensed under the MIT License – feel free to use, modify, and distribute.
