const http = require('http');

console.log('=== 检查 MCP 服务状态 ===\n');

const initB = JSON.stringify({
    jsonrpc: '2.0',
    method: 'initialize',
    params: {
        protocolVersion: '2024-11-05',
        capabilities: {},
        clientInfo: { name: 'test', version: '1.0.0' }
    },
    id: 1
});

const opt = {
    hostname: 'localhost',
    port: 18060,
    path: '/mcp',
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json, text/event-stream'
    }
};

const req = http.request(opt, (res) => {
    let body = '';
    res.on('data', chunk => body += chunk);
    res.on('end', () => {
        const r = JSON.parse(body);
        console.log('初始化结果:', JSON.stringify(r.result, null, 2));
        console.log('\n初始化 MCP 会话完成');
        console.log('\n发送通知...');
        
        const nB = JSON.stringify({
            jsonrpc: '2.0',
            method: 'notifications/initialized',
            params: {},
            id: null
        });
        
        const req2 = http.request(opt, (res2) => {
            let b2 = '';
            res2.on('data', chunk => b2 += chunk);
            res2.on('end', () => {
                console.log('通知发送成功');
                console.log('\n=== 检查用户信息 ===');
                
                const upB = JSON.stringify({
                    jsonrpc: '2.0',
                    method: 'tools/call',
                    params: {
                        name: 'user_profile',
                        arguments: {}
                    },
                    id: 2
                });
                
                const req3 = http.request(opt, (res3) => {
                    let b3 = '';
                    res3.on('data', chunk => b3 += chunk);
                    res3.on('end', () => {
                        console.log('\n用户信息:', JSON.stringify(JSON.parse(b3).result || JSON.parse(b3), null, 2));
                    });
                    req3.write(upB);
                    req3.end();
                });
                
                req2.on('error', (e) => {
                    console.log('请求 2 错误:', e.message);
                });
                req2.write(nB);
                req2.end();
            });
            
            req2.on('error', (e) => {
                console.log('请求 2 错误:', e.message);
            });
        });
        
        req.on('error', (e) => {
            console.log('请求错误:', e.message);
        });
        
        req.write(initB);
        req.end();
    });
    
    req.on('error', (e) => {
        console.log('请求错误:', e.message);
    });
});

req.write(initB);
req.end();
