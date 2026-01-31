import { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";

let client: Client | null = null;

function App() {
    const [count, setCount] = useState<number>(0);

    useEffect(() => {
        client = new Client({
            brokerURL: "ws://localhost:8080/ws",
            reconnectDelay: 5000,
            onConnect: () => {
                console.log("WS connected");
                client?.publish({
                    destination: "/app/current",
                    body: ""
                });
                client?.subscribe("/topic/count", (message) => {
                    setCount(JSON.parse(message.body));
                });


            },
            onStompError: (frame) => {
                console.error("Broker error", frame);
            },
        });

        client.activate();

        return () => {
            client?.deactivate();
        };
    }, []);

    const handleClick = () => {
        client?.publish({
            destination: "/app/click",
            body: "",
        });
    };

    return (
        <div style={{ padding: "40px" }}>
            <h1>Click Counter (WebSocket)</h1>
            <button onClick={handleClick}>Click me</button>
            <p>Count from server: {count}</p>
        </div>
    );
}

export default App;
