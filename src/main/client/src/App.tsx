import { useEffect, useState } from "react";

function App() {
    const [count, setCount] = useState<number>(0);

    // โหลดค่าครั้งแรกจาก backend
    useEffect(() => {
        fetch("http://localhost:8080/api/count")
            .then(res => res.json())
            .then(data => {setCount(data);});
    }, []);

    const handleClick = async () => {
        const res = await fetch("http://localhost:8080/api/click", {
            method: "POST",
        });

        const newCount = await res.json();
        setCount(newCount);
    };

    return (
        <div style={{ padding: "40px" }}>
            <h1>Click Counter (React + Spring Boot)</h1>
            <button onClick={handleClick}>Click me</button>
            <p>Count from server: {count}</p>
        </div>
    );
}

export default App;