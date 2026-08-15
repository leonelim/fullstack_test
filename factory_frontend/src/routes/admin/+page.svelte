<script lang="ts">
    let {data} = $props();
</script>
<h1>панель для создания пользователей</h1>
<form method="POST">
<input type="text" name="id" placeholder="id"/>
<input type="password" name="password" placeholder="password"/>
<button>send</button>
</form>
<a href="/">
home
</a>
<p>пользователи:</p>
{#each data.users as user (user.id)}
<li>user id: {user.id}</li>
{/each}
